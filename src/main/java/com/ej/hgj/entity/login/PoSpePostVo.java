package com.ej.hgj.entity.login;


import lombok.Data;

import java.io.Serializable;
@Data
public class PoSpePostVo implements Serializable{

	private static final long serialVersionUID = 4728545052645182529L;

	private String poSeqId;
	
	private String postId;

	private String postName;

}
