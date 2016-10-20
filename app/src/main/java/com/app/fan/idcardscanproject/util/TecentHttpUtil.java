package com.app.fan.idcardscanproject.util;

import android.util.Log;

import com.app.fan.idcardscanproject.Constant;
import com.app.fan.idcardscanproject.sign.YoutuSign;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.app.fan.idcardscanproject.Constant.EXPIRED_SECONDS;

/**
 * Created by Administrator on 2016/10/19.
 */

public class TecentHttpUtil {

    public static void uploadIdCard(String bitmap, String card_type, final SimpleCallBack callback) {
        StringBuffer mySign = new StringBuffer("");
        YoutuSign.appSign(Constant.AppID, Constant.SecretID, Constant.SecretKey,
                System.currentTimeMillis() / 1000 + EXPIRED_SECONDS,
                "", mySign);
        RequestParams params = new RequestParams("http://api.youtu.qq.com/youtu/ocrapi/idcardocr");
        params.setAsJsonContent(true);
        params.addHeader("accept", "*/*");
        params.addHeader("Host", "api.youtu.qq.com");
        params.addHeader("user-agent", "youtu-java-sdk");
        params.addHeader("Authorization", mySign.toString());
        params.addHeader("Content-Type", "text/json");
        params.addParameter("card_type", Integer.valueOf(card_type));
        params.addBodyParameter("image", bitmap);
        params.addBodyParameter("app_id", Constant.AppID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("succ+++++++++++++++","ppppppppppppppppp");
                callback.Succ(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("err+++++++++++++",ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("cancelerr+++++++++++++","ppppppppppppppppp");
            }

            @Override
            public void onFinished() {
                Log.d("finish+++++++++++++","ppppppppppppppppp");

            }
        });

    }

    public interface SimpleCallBack {
        void Succ(String result);

        void error();
    }


}
