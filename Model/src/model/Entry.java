package model;

import java.time.LocalDateTime;

public abstract class Entry {
	private LocalDateTime dataOra ;

	public Entry(LocalDateTime dataOra) {
		super();
		this.dataOra = dataOra;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}
	
}
