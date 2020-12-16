package org.felix.utils;

/**
 * @author Felix
 */
public final class LevelTools {

    public static String getLevelByPoint(int point){
        String level = "";
        if(point <= 80){
            level = "1";
        }
        else if(point > 80 && point <= 150){
            level = "2";
        }
        else if(point > 150 && point <= 280){
            level = "3";
        }
        else if(point > 280 && point <= 500){
            level = "4";
        }
        else if(point > 500 && point <= 800){
            level = "5";
        }
        else if(point > 800 && point <= 1200){
            level = "6";
        }
        else if(point > 1200 && point <= 2000){
            level = "7";
        }
        else if(point > 2000 && point <= 3000){
            level = "8";
        }
        else if(point > 3000 && point <= 5000){
            level = "9";
        }
        else if(point > 5000 && point <= 8000){
            level = "10";
        }
        else if(point > 8000 && point <= 12000){
            level = "11";
        }
        else if(point > 120000 && point <= 18000){
            level = "12";
        }
        else if(point > 18000 && point <= 25000){
            level = "13";
        }
        else if(point > 25000 && point <= 36000){
            level = "14";
        }
        else if(point > 36000 && point <= 50000){
            level = "15";
        }
        else if(point > 50000 && point <= 70000){
            level = "16";
        }
        else if(point > 70000 && point <= 100000){
            level = "17";
        }
        else if(point > 100000){
            level = "18";
        } else {
            level = "0";
        }
        return level;
    }

    public static String getLevelNameByPoint(int point, int sex){
        String levelName = "";
        String level = getLevelByPoint(point);//获取级别
        //性别(1：男；2：女；)
        switch(level){
            case "1" :
                levelName = sex == 1 ? "举人" : "秀女";
                break;
            case "2" :
                levelName = sex == 1 ? "里胥" : "答应";// li xu
                break;
            case "3" :
                levelName = sex == 1 ? "里正" : "青衣";
                break;
            case "4" :
                levelName = sex == 1 ? "巡检" : "常侍";
                break;
            case "5" :
                levelName = sex == 1 ? "驿丞" : "常在";
                break;
            case "6" :
                levelName = sex == 1 ? "县主" : "薄美人";
                break;
            case "7" :
                levelName = sex == 1 ? "县丞" : "贵人";
                break;
            case "8" :
                levelName = sex == 1 ? "知县" : "婕妤";//jie yu
                break;
            case "9" :
                levelName = sex == 1 ? "通判" : "婉仪";
                break;
            case "10" :
                levelName = sex == 1 ? "知州" : "修仪";
                break;
            case "11" :
                levelName = sex == 1 ? "知府" : "淑媛";
                break;
            case "12" :
                levelName = sex == 1 ? "按察使" : "昭仪";
                break;
            case "13" :
                levelName = sex == 1 ? "巡抚" : "贵嫔";
                break;
            case "14" :
                levelName = sex == 1 ? "大学士" : "妃子";
                break;
            case "15" :
                levelName = sex == 1 ? "宰相" : "贵妃";
                break;
            case "16" :
                levelName = sex == 1 ? "王爷" : "皇贵妃";
                break;
            case "17" :
                levelName = sex == 1 ? "皇帝" : "皇后";
                break;
            case "18" :
                levelName = sex == 1 ? "太上皇" : "皇太后";
                break;
            default :
                levelName = "乞丐";
                break;
        }
        return levelName;
    }
}
