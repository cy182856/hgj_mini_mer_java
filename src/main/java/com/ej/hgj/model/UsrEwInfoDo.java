package com.ej.hgj.model;

public class UsrEwInfoDo {
	
    private String custId;

    private String authCorpId;
    
    private String authCorpName;

    private String permanentCode;
    
    private String agentId;

    private String accessToken;

    private String createTokenTime;

    private Integer tokenExpire;

    private String stat;

    public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAuthCorpId() {
		return authCorpId;
	}

	public void setAuthCorpId(String authCorpId) {
		this.authCorpId = authCorpId;
	}

	public String getAuthCorpName() {
        return authCorpName;
    }

    public void setAuthCorpName(String authCorpName) {
        this.authCorpName = authCorpName;
    }
    
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCreateTokenTime() {
        return createTokenTime;
    }

    public void setCreateTokenTime(String createTokenTime) {
        this.createTokenTime = createTokenTime;
    }

    public Integer getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Integer tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}