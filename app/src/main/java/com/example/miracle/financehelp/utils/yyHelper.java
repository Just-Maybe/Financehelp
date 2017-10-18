package com.example.miracle.financehelp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.miracle.financehelp.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

public class yyHelper {
    private static final String GRAMMAR_TYPE_ABNF = "abnf";
    private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    private static yyHelper instance = null;
    onResultListener listener;
    private SpeechRecognizer mAsr;
    private SharedPreferences mSharedPreferences;
    String mContent;
    private Context mContext;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    int ret;
    private GrammarListener mCloudGrammarListener = new GrammarListener() {
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                String grammarID = new String(grammarId);
                SharedPreferences.Editor editor = yyHelper.this.mSharedPreferences.edit();
                if (!TextUtils.isEmpty(grammarId)) {
                    editor.putString(KEY_GRAMMAR_ABNF_ID, grammarID);
                }
                editor.commit();
                Toast.makeText(yyHelper.this.mContext, "语法构建成功" + grammarId, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(yyHelper.this.mContext, "语法构建失败，错误码" + error.getErrorCode(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("111", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        public void onBeginOfSpeech() {
        }

        public void onEndOfSpeech() {
        }

        public void onError(SpeechError error) {

//            Toast.makeText(yyHelper.this.mContext, "说话太快了!", Toast.LENGTH_SHORT).show();
        }

        public void onEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Bundle paramAnonymousBundle) {
        }

        public void onResult(final RecognizerResult result, boolean isLast) {
            if (result != null) {
                Log.d("111", "recognizer result:" + result.getResultString());
                String text;
                if ("cloud".equalsIgnoreCase(mEngineType)) {
                    if (result.getResultString() != null&&!isLast) {
                        text = JsonParser.parseGrammarResult(result.getResultString());
                        listener.onResult(text);
                    }
                }
            }
//            Log.d("111", "recognizer result : null");
        }

        public void onVolumeChanged(int paramAnonymousInt, byte[] paramAnonymousArrayOfByte) {
        }
    };


    private yyHelper(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        mAsr = SpeechRecognizer.createRecognizer(mContext, mInitListener);
//        Log.d("111", "yyHelper: " + String.valueOf(mAsr == null));
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, this.mCloudGrammarListener);
    }

    public static yyHelper getInstance(Context context) {
        if (instance == null) {
            instance = new yyHelper(context);
        }
        return instance;
    }


    public void startListening(onResultListener resultListener) {
        this.listener = resultListener;
        mAsr.startListening(mRecognizerListener);
    }

    public void stopListening() {
        mAsr.stopListening();
    }

    public interface onResultListener {
        void onResult(String paramString);
    }
}
