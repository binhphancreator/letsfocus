package com.app.letsfocus.ui.Setting;

public class SettingList {
        private String Title;
        private String Detail;

        public SettingList(String name, String tail) {
            this.Title = name;
            this.Detail = tail;
        }

        public String getItemTitle() {
            return this.Title;
        }

        public String getItemDetail() {
            return this.Detail;
        }
}

