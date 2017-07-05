package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class NodeM {
	@Id
	@SequenceGenerator(name = "GEN_PAI", sequenceName = "SEQ_PAI")
	@GeneratedValue(generator = "GEN_PAI")
	private int cd;
	@Column
	private String code;
	@Column
	private String description;

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	private NodeM parentId;
	@Transient
	private List<NodeM> children;
	@Transient
	private boolean hasChildren;

	@Column
	private String detail;

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public NodeM getParentId() {
		return parentId;
	}

	public void setParentId(NodeM parentId) {
		this.parentId = parentId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<NodeM> getChildren() {
		return children;
	}

	public void setChildren(List<NodeM> children) {
		this.children = children;
	}

	public void addChildren(NodeM children) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(children);
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

}
