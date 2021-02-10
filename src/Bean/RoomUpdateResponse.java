package Bean;

import java.util.ArrayList;
import java.util.List;

public class RoomUpdateResponse {
	protected boolean outcome;
	protected boolean sessionExpired;
	protected String subject;
	protected List<AbstractMinigame> minigameByTypeList1;
	protected List<AbstractMinigame> minigameByTypeList2;
	protected List<AbstractMinigame> minigameByTypeList3;
	protected int idMinigame1;
	protected int idMinigame2;
	protected int idMinigame3;
	protected String type1;
	protected String type2;
	protected String type3;
	protected String comboBoxSelected;
	protected List<AbstractMinigame> minigameByTypeList;
	
	
	public RoomUpdateResponse() {
		this.outcome=false;
		this.sessionExpired=false;
		this.comboBoxSelected="";
		this.minigameByTypeList1=new ArrayList<AbstractMinigame>();
		this.minigameByTypeList2=new ArrayList<AbstractMinigame>();
		this.minigameByTypeList3=new ArrayList<AbstractMinigame>();
		this.minigameByTypeList=new ArrayList<AbstractMinigame>();
	}
	public List<AbstractMinigame> getMinigameByTypeList() {
		return minigameByTypeList;
	}
	public void setMinigameByTypeList(List<AbstractMinigame> minigameByTypeList) {
		this.minigameByTypeList = minigameByTypeList;
	}
	public String getComboBoxSelected() {
		return comboBoxSelected;
	}
	public void setComboBoxSelected(String comboBoxSelected) {
		this.comboBoxSelected = comboBoxSelected;
	}
	public boolean isOutcome() {
		return outcome;
	}
	public void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}
	public boolean isSessionExpired() {
		return sessionExpired;
	}
	public void setSessionExpired(boolean sessionExpired) {
		this.sessionExpired = sessionExpired;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<AbstractMinigame> getMinigameByTypeList1() {
		return minigameByTypeList1;
	}
	public void setMinigameByTypeList1(List<AbstractMinigame> minigameByTypeList1) {
		this.minigameByTypeList1 = minigameByTypeList1;
	}
	public List<AbstractMinigame> getMinigameByTypeList2() {
		return minigameByTypeList2;
	}
	public void setMinigameByTypeList2(List<AbstractMinigame> minigameByTypeList2) {
		this.minigameByTypeList2 = minigameByTypeList2;
	}
	public List<AbstractMinigame> getMinigameByTypeList3() {
		return minigameByTypeList3;
	}
	public void setMinigameByTypeList3(List<AbstractMinigame> minigameByTypeList3) {
		this.minigameByTypeList3 = minigameByTypeList3;
	}
	public int getIdMinigame1() {
		return idMinigame1;
	}
	public void setIdMinigame1(int idMinigame1) {
		this.idMinigame1 = idMinigame1;
	}
	public int getIdMinigame2() {
		return idMinigame2;
	}
	public void setIdMinigame2(int idMinigame2) {
		this.idMinigame2 = idMinigame2;
	}
	public int getIdMinigame3() {
		return idMinigame3;
	}
	public void setIdMinigame3(int idMinigame3) {
		this.idMinigame3 = idMinigame3;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	
}
