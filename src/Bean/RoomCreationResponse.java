package Bean;

import java.util.ArrayList;
import java.util.List;

public class RoomCreationResponse {
	protected boolean outcome;
	protected boolean sessionExpired;
	protected List<Subject> subjectList;
	protected String comboBoxSelected;
	protected List<AbstractMinigame> minigameByTypeList;

	public RoomCreationResponse() {
		this.outcome=false;
		this.sessionExpired=false;
		this.subjectList=new ArrayList<Subject>();
		this.minigameByTypeList=new ArrayList<AbstractMinigame>();
		this.comboBoxSelected="";
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

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public String getComboBoxSelected() {
		return comboBoxSelected;
	}

	public void setComboBoxSelected(String comboBoxSelected) {
		this.comboBoxSelected = comboBoxSelected;
	}

	public List<AbstractMinigame> getMinigameByTypeList() {
		return minigameByTypeList;
	}

	public void setMinigameByTypeList(List<AbstractMinigame> minigameByTypeList) {
		this.minigameByTypeList = minigameByTypeList;
	}
	
}
