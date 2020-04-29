package com.dap.cloud.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.text.SimpleDateFormat;

public class PushUtil implements Runnable {
    // protected static final Logger LOG =
    // LoggerFactory.getLogger(PushExample.class);

    // 0员工版,1家属版
    private static final String[] appKeys = {"ab7172f89356d66b3e13c733"};
    private static final String[] masterSecrets = {"dcac66f592cc5213f9bc807e"};
    // demo App defined in resources/jpush-api.conf
    // APPkey
    private static String appKey = "";
    // 推送key
    private static String masterSecret = "";
    // private static final String appKey ="9550195f6e170c26161af561";
    // private static final String masterSecret = "ca7de0fc5561f991bf3e1850";
    private String title;
    private String message;
    private String user;

    public static void main(String[] args) {
        // sendNotificationPush("维修任务",
        // "{\"roomName\":\"C-03\",\"content\":\"房间需要进行维修\",\"type\":\"维修任务\"}",
        // "qingjie");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // sendMessagePush("维修任务",
        // "{\"roomName\":\"C-08\",\"content\":\"房间需要进行维修\",\"type\":\"维修任务\",\"oId\":\"223423412124\",\"time\":\""+sdf.format(new
        // Date())+"\"}", "huangshan");
        // PushUtil pushUtil = new
        // PushUtil("维修任务","{\"roomName\":\"C-08\",\"content\":\"房间需要进行维修\",\"type\":\"维修任务\",\"oId\":\"223423412124\",\"time\":\""+sdf.format(new
        // Date())+"\"}","wangmin");
        // Thread t = new Thread(pushUtil);
        // t.start();
        PushUtil pushUtil = new PushUtil("会员发布任务", "{\"flag\":\"1\"}",
                "18615332224", 0);
        // PushUtil pushUtil = new
        // PushUtil("清洁任务","{\"id\":\"asdasdasdas\",\"content\":\"asdasd\",\"time\":\"asdasdasd\",\"personId\":\"asdasdasda\",\"personName\":\"asdasdqw\",\"type\":\"asdqw\",\"status\":\"asd\"}","demo");
        //System.out.println(pushUtil);
        Thread t = new Thread(pushUtil);
        t.start();
    }

    public static String sendNotificationPush(String title, String alert,
                                              String... alias) {
        try {
            JPushClient jpushClient = new JPushClient(masterSecret, appKey);
            PushPayload payload = sendNotification(title, alert, alias);
            PushResult result = jpushClient.sendPush(payload);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendMessagePush(String title, String alert,
                                         String... alias) {
        try {
            System.out.println(title + "--------------------------------");
            JPushClient jpushClient = new JPushClient(masterSecret, appKey);
            PushPayload payload = sendMessage(title, alert, alias);
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(alert);
            System.out.println(result.toString()
                    + "------------------------------");
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PushPayload sendNotification(String title, String alert,
                                               String... alias) {
        if (alias == null || alias.length == 0) {
            return PushPayload
                    .newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.all())
                    .setNotification(
                            Notification
                                    .newBuilder()
                                    .setAlert(alert)
                                    .addPlatformNotification(
                                            AndroidNotification.newBuilder()
                                                    .setTitle(title).build())
                                    .addPlatformNotification(
                                            IosNotification.newBuilder()
                                                    .incrBadge(1)
                                                    .addExtra("title", title)
                                                    .build()).build()).build();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(
                        Audience.newBuilder()
                                .addAudienceTarget(AudienceTarget.alias(alias))
                                .build())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder()
                                                .setTitle(title).build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder()
                                                .incrBadge(1)
                                                .addExtra("title", title)
                                                .build()).build()).build();
    }

    public static PushPayload sendMessage(String title, String message,
                                          String... alias) {
        if (alias == null || alias.length == 0) {
            return PushPayload
                    .newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.all())
                    .setMessage(
                            Message.newBuilder().setMsgContent(message)
                                    .addExtra("title", title)
                                    .addExtra("message", message).build())
                    .build();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(
                        Audience.newBuilder()
                                .addAudienceTarget(AudienceTarget.alias(alias))
                                .build())
                .setMessage(
                        Message.newBuilder().setMsgContent(message)
                                .addExtra("title", title)
                                .addExtra("message", message).build()).build();
    }

    @Override
    public void run() {
        if (StringUtils.isNoneBlank(this.appKey))
            sendMessagePush(this.title, this.message, this.user);
    }

    public PushUtil(String title, String message, String user, int index) {
        super();
        this.title = title;
        this.message = message;
        this.user = user;
        if (index >= 0 && index < appKeys.length) {
            this.appKey = appKeys[index];
            this.masterSecret = masterSecrets[index];
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}