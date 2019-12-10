package com.gm.soft.sqlite;

import org.json.JSONException;
import org.json.JSONObject;

public class TrainTypeModel {
    private String trainTypeId;
    private String trainTypeName;

    public TrainTypeModel() {
    }

    public TrainTypeModel(JSONObject jsonObject) {
        try {
            this.trainTypeId = jsonObject.getString("id");
            this.trainTypeName = jsonObject.getString("name");
        } catch (JSONException e) {
        }
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getTrainTypeName() {
        return trainTypeName;
    }

    public void setTrainTypeName(String trainTypeName) {
        this.trainTypeName = trainTypeName;
    }

    @Override
    public String toString() {
        return trainTypeName;
    }
}
