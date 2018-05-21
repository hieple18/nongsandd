package com.models;

import java.util.List;

import com.entity.Hamlet;

/**
*@author HiepLe
*@version 1.0 Dec 13, 2017
*/
public class ResponseHamlet {
    private List<Hamlet> response;

    public ResponseHamlet(List<Hamlet> message) {
        this.response = message;
    }

    public List<Hamlet> getResponse() {
        return response;
    }

    public void setResponse(List<Hamlet> response) {
        this.response = response;
    }
}
