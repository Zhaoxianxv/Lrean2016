package com.zxx.lrean.lrean2016.modle;

import java.util.List;

/**
 * Created by yfy1 on 2016/8/1.
 */
public class Infter  {
    private  List<NewsDataBean> Websitemenu2 ;

    private  String islast;

    private  String programa_id;

    private  String programa_name;

    public void setWebsitemenu2(List<NewsDataBean> Websitemenu2){
        this.Websitemenu2 = Websitemenu2;
    }
    public List<NewsDataBean> getWebsitemenu2(){
        return this.Websitemenu2;
    }
    public void setIslast(String islast){
        this.islast = islast;
    }
    public String getIslast(){
        return this.islast;
    }
    public void setPrograma_id(String programa_id){
        this.programa_id = programa_id;
    }
    public String getPrograma_id(){
        return this.programa_id;
    }
    public void setPrograma_name(String programa_name){
        this.programa_name = programa_name;
    }
    public String getPrograma_name(){
        return this.programa_name;
    }
    public static class NewsDataBean {
        private  String islast;

        private  String programa_id;

        private  String programa_name;

        public void setIslast(String islast){
            this.islast = islast;
        }
        public String getIslast(){
            return this.islast;
        }
        public void setPrograma_id(String programa_id){
            this.programa_id = programa_id;
        }
        public String getPrograma_id(){
            return this.programa_id;
        }
        public void setPrograma_name(String programa_name){
            this.programa_name = programa_name;
        }
        public String getPrograma_name(){
            return this.programa_name;
        }


    }


}
