package io.renren.common.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.renren.common.exception.RRException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

public class ImageUtils {
    //设置APPID/AK/SK
    public static final String API_KEY = "uxuGMog2tjbQImMWOGoogbBn";
    public static final String SECRET_KEY = "9a0Qg620T9Dgg44NnE1fsOplwUG8Wf1q";

    public static final String TEXT_API_KEY = "zAnlWaqBBFXhoWxeMDiOO4rb";
    public static final String TEXT_SECRET_KEY = "b3vAdRhph2nvptWosxLA7c1oIZcr7Mwu";
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();


    public static void main(String[] args) throws IOException {
        String imageText = getImageText("https://educ-rjw.oss-cn-hangzhou.aliyuncs.com/exam/1684908727284_answer1.png");
        System.out.printf("imageTest:\n" + imageText);
//        int score = getScore(imageText, "数学学期2023上学期老师教师亲和力1教师教师课堂氛围", 20);
//        System.out.printf("score:" + score);
    }

    public static int getScore(String origin, String target, int maxScore) {
        if (StrUtil.isBlank(target)) {
            return 0;
        }
        MediaType mediaType = MediaType.parse("application/json");
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("text_1", origin);
        jsonObject.put("text_2", target);
        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet?charset=&access_token=" + getAccessToken(TEXT_API_KEY, TEXT_SECRET_KEY))
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        double similarity = 0.0;
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                com.alibaba.fastjson.JSONObject scoreObject = com.alibaba.fastjson.JSONObject.parseObject(responseBody.string());
                String scoreStr = scoreObject.getString("score");
                similarity = Double.parseDouble(scoreStr);
            }
        } catch (IOException ignored) {
        }
        int score = (int) (similarity * maxScore);
        System.out.print("相似度 = " + similarity + ",score = " + score);
        return Math.min(score, maxScore);
    }

    public static String getImageText(String imageUrl) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "url=" + URLEncoder.encode(imageUrl));
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting?access_token=" + getAccessToken(API_KEY, SECRET_KEY))
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        StringBuilder content = new StringBuilder();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (response.body() != null) {
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response.body().string());
                JSONArray wordsResult = jsonObject.getJSONArray("words_result");
                if (!wordsResult.isEmpty()) {
                    List<com.alibaba.fastjson.JSONObject> wordsJson = wordsResult.stream().map(item -> com.alibaba.fastjson.JSONObject.parseObject(JSON.toJSONString(item), com.alibaba.fastjson.JSONObject.class)).collect(Collectors.toList());
                    List<String> words = wordsJson.stream().map(item -> item.getString("words")).collect(Collectors.toList());
                    words.forEach(content::append);
                }
            }
        } catch (IOException e) {
            throw new RRException("图片失败失败");
        }

        return content.toString();
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    private static String getAccessToken(String apiKey, String apiSecret) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + apiKey
                + "&client_secret=" + apiSecret);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            if (response.body() != null) {
                return new JSONObject(response.body().string()).getString("access_token");
            } else {
                throw new RRException("图片失败鉴权失败");
            }
        } catch (IOException e) {
            throw new RRException("图片失败鉴权失败");
        }
    }
}
