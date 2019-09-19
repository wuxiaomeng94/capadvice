package com.capinfo.utils;

import com.capinfo.enumUtils.DictEntity;
import com.capinfo.enumUtils.SendCaseTypeEnum;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class DictUtils {

    /**
     * 获取字典。暂时这样取枚举里的值
     * @param type
     * @return
     */
    public static List<DictEntity> getDictList(String type) {
        List<DictEntity> dictList = new ArrayList<>();
        if ("sendCaseType".equals(type)) {
            List<SendCaseTypeEnum> enumList = EnumUtils.getEnumList(SendCaseTypeEnum.class);
            enumList.forEach((sendCaseTypeEnum) -> {
                DictEntity dict = new DictEntity();
                dict.setKey(sendCaseTypeEnum.getType());
                dict.setValue(sendCaseTypeEnum.getName());
                dictList.add(dict);
            });
        }
        return dictList;
    }


}
