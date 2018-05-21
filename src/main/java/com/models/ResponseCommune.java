package com.models;

import java.util.List;

import com.entity.Commune;

/**
*@author HiepLe
*@version 1.0 Dec 14, 2017
*/
public class ResponseCommune {
    private List<Commune> response;

    public ResponseCommune(List<Commune> message) {
        this.response = message;
    }

    public List<Commune> getResponse() {
        return response;
    }

    public void setResponse(List<Commune> response) {
        this.response = response;
    }

}
