package com.unity;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Aniket Savanand
 *
 */
public class Project {
	private int id;
	private String projectName;
	private String creationDate;
	private String expiryDate;
	private String enabled;
	private String[] targetCountries;
	private double projectCost;
	private String projectUrl;
	private ArrayList<TargetKey> targetKeys;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", creationDate=" + creationDate + ", expiryDate="
				+ expiryDate + ", enabled=" + enabled + ", targetCountries=" + Arrays.toString(targetCountries)
				+ ", projectCost=" + projectCost + ", projectUrl=" + projectUrl + ", targetKeys=" + targetKeys + "]";
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	
	public double getProjectCost() {
		return projectCost;
	}
	/**
	 * @param projectCost the projectCost to set
	 */
	public void setProjectCost(double projectCost) {
		this.projectCost = projectCost;
	}
	/**
	 * @return the projectUrl
	 */
	public String getProjectUrl() {
		return projectUrl;
	}
	/**
	 * @param projectUrl the projectUrl to set
	 */
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	
	public String[] getTargetCountries() {
		return targetCountries;
	}
	public void setTargetCountries(String[] targetCountries) {
		this.targetCountries = targetCountries;
	}
	public ArrayList<TargetKey> getTargetKeys() {
		return targetKeys;
	}
	public void setTargetKeys(ArrayList<TargetKey> targetKeys) {
		this.targetKeys = targetKeys;
	}
	
	
}
