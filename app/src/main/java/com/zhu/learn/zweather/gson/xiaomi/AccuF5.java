package com.zhu.learn.zweather.gson.xiaomi;

import java.util.List;

/**
 * Created by zhu on 2017/3/14.
 */

public class AccuF5 {
    private String EffectiveEpochDate;

    private String EffectiveDate;

    private List<AccuF5DailyForecasts> DailyForecasts;

    public List<AccuF5DailyForecasts> getDailyForecasts() {
        return DailyForecasts;
    }

    public void setDailyForecasts(List<AccuF5DailyForecasts> dailyForecasts) {
        DailyForecasts = dailyForecasts;
    }

    public void setEffectiveEpochDate(String EffectiveEpochDate){
        this.EffectiveEpochDate = EffectiveEpochDate;
    }
    public String getEffectiveEpochDate(){
        return this.EffectiveEpochDate;
    }
    public void setEffectiveDate(String EffectiveDate){
        this.EffectiveDate = EffectiveDate;
    }
    public String getEffectiveDate(){
        return this.EffectiveDate;
    }

    public class AccuF5DailyForecasts {
        private String Date;

        private String EpochDate;

        private String Sun_Rise;

        private String Sun_EpochRise;

        private String Sun_Set;

        private String Sun_EpochSet;

        private String PrecipitationProbability;

        public void setDate(String Date){
            this.Date = Date;
        }
        public String getDate(){
            return this.Date;
        }
        public void setEpochDate(String EpochDate){
            this.EpochDate = EpochDate;
        }
        public String getEpochDate(){
            return this.EpochDate;
        }
        public void setSun_Rise(String Sun_Rise){
            this.Sun_Rise = Sun_Rise;
        }
        public String getSun_Rise(){
            return this.Sun_Rise;
        }
        public void setSun_EpochRise(String Sun_EpochRise){
            this.Sun_EpochRise = Sun_EpochRise;
        }
        public String getSun_EpochRise(){
            return this.Sun_EpochRise;
        }
        public void setSun_Set(String Sun_Set){
            this.Sun_Set = Sun_Set;
        }
        public String getSun_Set(){
            return this.Sun_Set;
        }
        public void setSun_EpochSet(String Sun_EpochSet){
            this.Sun_EpochSet = Sun_EpochSet;
        }
        public String getSun_EpochSet(){
            return this.Sun_EpochSet;
        }
        public void setPrecipitationProbability(String PrecipitationProbability){
            this.PrecipitationProbability = PrecipitationProbability;
        }
        public String getPrecipitationProbability(){
            return this.PrecipitationProbability;
        }
    }
}
