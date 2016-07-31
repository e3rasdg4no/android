package com.eragano.eraganoapps.Chat;

import android.app.Application;
import android.util.Log;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Sample breadcrumb
        ZopimChat.trackEvent("Application created");

        /**
         * Minimum chat configuration. Chat must be initialization before starting the chat.
         */
        ZopimChat.init(AccountKey.ACCOUNT_KEY);

        // Uncomment to see how global configuration is set during initialization. This will be the default chat configuration for all chat sessions.
        {
//            /**
//             * Define default pre-chat form requirements for all of chat. You will have a chance to provide special case requirements when starting a chat.
//             */
//            PreChatForm defaultPreChat = new PreChatForm.Builder()
//                    .email(PreChatForm.Field.OPTIONAL)
//                    .department(PreChatForm.Field.OPTIONAL)
//                    .phoneNumber(PreChatForm.Field.OPTIONAL)
//                    .build();
//
//            /**
//             * Global chat configuration
//             */
//            ZopimChat.init(AccountKey.ACCOUNT_KEY)
//                    .preChatForm(defaultPreChat)
//                    .department("The date")
//                    .tags("sample")
//                    .disableVisitorInfoStorage();
//
//            /**
//             * Specify visitor data. This can be done at any point but it will apply at every chat startup.
//             */
//            VisitorInfo visitorData = new VisitorInfo.Builder()
//                    .name("Sample Visitor")
//                    .phoneNumber("+1800111222333")
//                    .email("visitor@example.com")
//                    .build();
//
//            ZopimChat.setVisitorInfo(visitorData);
        }

        // clear visitor info. Visitor info storage can be disabled at chat initialization
        VisitorInfo emptyVisitorInfo = new VisitorInfo.Builder().build();
        ZopimChat.setVisitorInfo(emptyVisitorInfo);
        Log.v("Zopim Chat Sample", "Visitor info erased.");
    }
}
