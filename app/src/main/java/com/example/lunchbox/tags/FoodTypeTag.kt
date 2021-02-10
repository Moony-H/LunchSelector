package com.example.lunchbox.tags

class FoodTypeTag {
    companion object{
        const val KOREANFOOD=0
        const val WESTERNFOOD=1
        const val CHINESEFOOD=2
        const val JAPANESEFOOD=3
        fun getStringWithFoodTypeTag(tag:Int):String {

            return when(tag){
                0->"KoreanFood"
                1->"WesternFood"
                2->"ChineseFood"
                3->"JapaneseFood"
                else->"NoTag"
            }

            //return if(tag==0)
            //    "KoreanFood"
            //else if(tag==1)
            //    "WesternFood"
            //else if(tag==2)
            //    "ChineseFood"
            //else if(tag==3)
            //    "JapaneseFood"
            //else
            //    "NoTag"
        }

        fun getKoreanStringWithFoodTypeTag(tag:Int):String {

            return when(tag){
                0->"한식"
                1->"양식"
                2->"중식"
                3->"일식"
                else->"없음"
            }

            //return if(tag==0)
            //    "한식"
            //else if(tag==1)
            //    "양식"
            //else if(tag==2)
            //    "중식"
            //else if(tag==3)
            //    "일식"
            //else
            //    "없음"
        }


    }
}