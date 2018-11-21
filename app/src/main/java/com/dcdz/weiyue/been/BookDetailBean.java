package com.dcdz.weiyue.been;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LJW on 2018/10/15.
 */
public class BookDetailBean implements Serializable {

    /**
     * rating : {"max":10,"numRaters":61130,"average":"8.4","min":0}
     * subtitle : 心智成熟的旅程
     * author : ["[美] M·斯科特·派克"]
     * pubdate : 2007-1
     * tags : [{"count":31070,"name":"心理学","title":"心理学"},{"count":17478,"name":"少有人走的路","title":"少有人走的路"},{"count":17161,"name":"成长","title":"成长"},{"count":12811,"name":"心理","title":"心理"},{"count":10142,"name":"心智","title":"心智"},{"count":9656,"name":"励志","title":"励志"},{"count":6481,"name":"人生","title":"人生"},{"count":5092,"name":"爱","title":"爱"}]
     * origin_title : The Road Less Traveled
     * image : https://img3.doubanio.com/view/subject/m/public/s2144391.jpg
     * binding : 平装
     * translator : ["于海生"]
     * catalog : 中文版序
     25周年版序言
     前言
     第一部分 自律
     问题和痛苦
     推迟满足感
     父母的过错
     解决问题的时机
     承担责任
     神经官能症与人格失调
     逃避自由
     尊重理实
     移情：过时的地图
     迎接挑战
     隐瞒真相
     保持平衡
     抑郁的价值
     放弃与新生
     第二部分 爱
     爱的定义
     陷入情网
     浪漫爱情的神话
     自我界限
     依赖性
     精神贯注
     “自我牺牲”
     爱，不是感觉
     关注的艺术
     死亡的风险
     独立的风险
     承诺的风险
     冲突的风险
     爱的自律
     爱与独立
     爱与心理治疗
     爱的神秘性
     第三部分 成长与宗教
     宗教与世界观
     科学的宗教
     凯茜的故事
     马西娅的故事
     特德的故事
     婴儿与洗澡水
     第四部分 神奇的力量
     后记
     * pages : 231
     * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s2144391.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s2144391.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s2144391.jpg"}
     * alt : https://book.douban.com/subject/1775691/
     * id : 1775691
     * publisher : 吉林文史出版社
     * isbn10 : 7807023775
     * isbn13 : 9787807023777
     * title : 少有人走的路
     * url : https://api.douban.com/v2/book/1775691
     * alt_title : The Road Less Traveled
     * author_intro : M·斯科特·派克，我们这个时代最杰出的心理医生，他的杰出不仅在其智慧，更在于他的真诚和勇气。儿童时，他就以“童言无忌”远近闻名；少年时，他又勇敢地放弃了父母为他安排的辉煌前程，毅然选择了自己的人生道路，最终当上了一名心理医生。他曾在美军日本冲绳基地担任心理医生，为美军军官做心理医生。在近二十年的职业生涯中，他治俞了成千上万个病人，他以从业经验为基础写作的《少有人走的路》，创造了出版史上的一大奇迹。
     * summary : 这本书处处透露出沟通与理解的意味，它跨越时代限制，帮助我们探索爱的本质，引导我们过上崭新，宁静而丰富的生活；它帮助我们学习爱，也学习独立；它教诲我们成为更称职的、更有理解心的父母。归根到底，它告诉我们怎样找到真正的自我。
     正如开篇所言：人生苦难重重。M·斯科特·派克让我们更加清楚：人生是一场艰辛之旅，心智成熟的旅程相当漫长。但是，他没有让我们感到恐惧，相反，他带领我们去经历一系列艰难乃至痛苦的转变，最终达到自我认知的更高境界。
     * series : {"id":"12495","title":"少有人走的路"}
     * price : 26.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private SeriesBean series;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<String> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 61130
         * average : 8.4
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/subject/s/public/s2144391.jpg
         * large : https://img3.doubanio.com/view/subject/l/public/s2144391.jpg
         * medium : https://img3.doubanio.com/view/subject/m/public/s2144391.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class SeriesBean {
        /**
         * id : 12495
         * title : 少有人走的路
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TagsBean {
        /**
         * count : 31070
         * name : 心理学
         * title : 心理学
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
