package com.app.letsfocus.ui.ReportApp;

import java.util.ArrayList;

public class groupItem {
        public String name;
        public ArrayList<childItem> child = new ArrayList<childItem>();

        public groupItem(String n){
            this.name = n;
        }

        public String getName(){
            return this.name;
        }
}
