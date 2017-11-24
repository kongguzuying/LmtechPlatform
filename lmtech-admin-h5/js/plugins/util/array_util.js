define('array_util', ['jquery', 'jquery_ui', 'constant'], function ($, ui, C) {
    return {
        /**
         * 找出两个数组中的相同元素
         * @param array1
         * @param array2
         * @returns {Array}
         */
        getSameDataOfArray: function (array1, array2) {
            //去掉数组1中重复的数据
            array1 = this._trimSameData(array1);
            //去掉数组2中重复的数据
            array2 = this._trimSameData(array2);
            //连接两数组中的元素
            var item = array1.concat(array2);
            //进行排序，使用系统的元素排列在一起
            item.sort();
            //存储两个数组相同元素的数组
            var sameDataArray = [];
            //从第二个元素开始遍历数组,与前者比较
            for (var i = 1; i < item.length; i++) {
                //判断与前面的元素是否相同，且是否已经保存到sameDataArray数组了
                if (item[i] == item[i - 1] && sameDataArray[sameDataArray.length - 1] != item[i]) {
                    sameDataArray.push(item[i]);//添加两个数组相同的元素
                }
            }
            return sameDataArray;
        },
        /**
         * 找出两个数组中的不同元素
         * @param array1
         * @param array2
         * @returns {Array}
         */
        getNotSameDataOfArray: function (array1, array2) {
            //去掉数组1中重复的数据
            array1 = this._trimSameData(array1);
            //去掉数组2中重复的数据
            array2 = this._trimSameData(array2);
            //连接两数组中的元素
            var item = array1.concat(array2);
            //进行排序，使用系统的元素排列在一起
            item.sort();
            //存储两个数组相同元素的数组
            var sameData = null;
            var notSameDataArray = [];
            //从第二个元素开始遍历数组,与前者比较
            for (var i = 1; i < item.length; i++) {
                //判断与前面的元素是否相同，且是否已经保存到sameDataArray数组了
                if (item[i] == item[i - 1] && sameData != item[i]) {
                    sameData = item[i];
                    var notSameData = notSameDataArray.get(notSameDataArray.length - 1);
                    if (sameData == notSameData) {
                        notSameDataArray.pop();
                    }
                } else {
                    if (item[i] != sameData) {
                        notSameDataArray.push(item[i]);
                    }
                }
            }
            return notSameDataArray;
        },
        _trimSameData: function (array) {
            //正则表达式，验证数据连续重复
            var reg = /,(.+)\1+/gim;
            var arrayStr = "," + array.sort().toString() + ",";
            //考虑这样的字符串怎么去重 ,1,1,3,3,34,4,4,5,8,9,9
            while (reg.test(arrayStr)) {
                arrayStr = arrayStr.replace(reg, ",$1");
            }
            arrayStr = arrayStr.slice(1, arrayStr.length - 1);
            return arrayStr.split(",");
        }
    }
})