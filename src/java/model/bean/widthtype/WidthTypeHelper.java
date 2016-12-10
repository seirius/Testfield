/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.widthtype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import util.enums.BlockWidthTypeEnum;

/**
 *
 * @author Andriy
 */
public class WidthTypeHelper {
    
    private BlockWidthTypeEnum widthType;
    private int widthTypeId;
    private int amount;
    
    public WidthTypeHelper () {}
    
    public WidthTypeHelper(int widthTypeId, int amount) {
        this.widthTypeId = widthTypeId;
        this.amount = amount;
        
        widthType = BlockWidthTypeEnum.getWidthTypeEnum(widthTypeId);
    }

    public int getWidthTypeId() {
        return widthTypeId;
    }

    public void setWidthTypeId(int widthTypeId) {
        this.widthTypeId = widthTypeId;
    }
    
    public WidthTypeHelper(BlockWidthTypeEnum widthType, int amount) {
        this.widthType = widthType;
        this.amount = amount;
    }

    public BlockWidthTypeEnum getWidthType() {
        return widthType;
    }

    public void setWidthType(BlockWidthTypeEnum widthType) {
        this.widthType = widthType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public void parseOwnWidthType() {
        widthType = BlockWidthTypeEnum.getWidthTypeEnum(widthTypeId);
    }
    
    public static List<WidthTypeHelper> parseWidthTypes(String widthTypes) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<WidthTypeHelper> widthTypeList = mapper.readValue(widthTypes, new TypeReference<List<WidthTypeHelper>>() {});
        widthTypeList.forEach((auxWidthType) -> {
            auxWidthType.parseOwnWidthType();
        });
        
        return widthTypeList;
    }
    
}
