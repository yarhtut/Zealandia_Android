package info.Zealandia.model;

/**
 * Created by 21104216 on 30/03/2015.
 */
public class SanctuaryView  {
    private String list_id;
    private String list_cat;
    private String list_name;
    private String list_img;
    private String list_song;
    private String list_desc;
    private String list_points;
    private String list_active;

    public SanctuaryView(){

    }

    public SanctuaryView(String list_id, String list_cat,
                         String list_name, String list_img, String list_song,
                         String list_desc, String list_points,
                         String list_active) {
        this.list_id = list_id;
        this.list_cat = list_cat;
        this.list_name = list_name;
        this.list_img = list_img;
        this.list_song = list_song;
        this.list_desc = list_desc;
        this.list_points = list_points;
        this.list_active = list_active;

    }



    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    public String getList_cat() {
        return list_cat;
    }

    public void setList_cat(String list_cat) {
        this.list_cat = list_cat;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getList_img() {
        return "http://yar.cloudns.org/SlimApi/template/include/images/" + list_img;
    }

    public void setList_img(String list_img) {
        this.list_img = list_img;
    }



    public String getList_song() {
        return list_song;
    }

    public void setList_song(String list_song) {
        this.list_song = list_song;
    }

    public String getList_desc() {
        return list_desc;
    }

    public void setList_desc(String list_desc) {
        this.list_desc = list_desc;
    }

    public String getList_points() {
        return list_points;
    }

    public void setList_points(String list_points) {
        this.list_points = list_points;
    }

    public String getList_active() {
        return list_active;
    }

    public void setList_active(String list_active) {
        this.list_active = list_active;
    }




}
