package com.example.admin.jjypznglxt.pro;

/**
 * Created by admin on 2015/9/22.
 */
public class Images {
    private String title;
    private int Beijing;
    private int Tubiao;

    public Images() {
    }

    public Images( int Beijing, int Tubiao,String title) {
        this.title = title;
        this.Beijing = Beijing;
        this.Tubiao = Tubiao;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBeijing() {
        return Beijing;
    }

    public void setBeijing(int beijing) {
        Beijing = beijing;
    }

    public int getTubiao() {
        return Tubiao;
    }

    public void setTubiao(int tubiao) {
        Tubiao = tubiao;
    }
}
