package model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GenericReturn implements Serializable {

	private List<NodeM> list;
	private NodeM object;
	private String message;
	private boolean sucess;

	public List<NodeM> getList() {
		return list;
	}

	public void setList(List<NodeM> list) {
		this.list = list;
	}

	public NodeM getObject() {
		return object;
	}

	public void setObject(NodeM object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

}
