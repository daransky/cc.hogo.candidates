package com.hogo.portal.statistics;

public class StatisticsEntry {

	String skill;
	int sum;
	int free;
	int reserved;
	int busy;

	StatisticsEntry(String name, int int1, int int2, int int3, int int4) {
		this.skill = name;
		sum = int1;
		free = int2;
		reserved = int3;
		busy = int4;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getFree() {
		return free;
	}

	public void setFree(int free) {
		this.free = free;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getBusy() {
		return busy;
	}

	public void setBusy(int busy) {
		this.busy = busy;
	}

}
