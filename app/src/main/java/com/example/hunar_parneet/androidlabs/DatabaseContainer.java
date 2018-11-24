package com.example.hunar_parneet.androidlabs;

import java.util.ArrayList;
import java.util.List;

public class DatabaseContainer {

        private List<MessageResult> msgResult = new ArrayList<MessageResult>();

        /**
         *
         * @return
         * The content
         */
        public List<MessageResult> getContent() {
            return msgResult;
        }

        /**
         *
         * @param content
         * The content
         */
        public void setContent(List<MessageResult> content) {
            this.msgResult = content;
        }


    }

