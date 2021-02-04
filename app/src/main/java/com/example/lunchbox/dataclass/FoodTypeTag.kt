package com.example.lunchbox.dataclass

class FoodTypeTag {
    companion object{
        const val KOREANFOOD=0
        const val WESTERNFOOD=1
        const val CHINESEFOOD=2
        const val JAPANESEFOOD=3
        fun getStringWithFoodTypeTag(tag:Int):String {
            return if(tag==0)
                "KoreanFood"
            else if(tag==1)
                "WesternFood"
            else if(tag==2)
                "ChineseFood"
            else if(tag==3)
                "JapaneseFood"
            else
                "NoTag"
        }

        fun getKoreanStringWithFoodTypeTag(tag:Int):String {
            return if(tag==0)
                "한식"
            else if(tag==1)
                "양식"
            else if(tag==2)
                "중식"
            else if(tag==3)
                "일식"
            else
                "없음"
        }


    }
}