package core.messager.dochie.bean;

public class group_dochie {
private long _idGrb;
private String _namaGrb;


public group_dochie() {
	super();
}

public group_dochie(long _idGrb) {
	super();
	this._idGrb = _idGrb;
}
public group_dochie(String _namaGrb) {
	super();
	this._namaGrb = _namaGrb;
}

public group_dochie(long _idGrb, String _namaGrb) {
	super();
	this._idGrb = _idGrb;
	this._namaGrb = _namaGrb;
}


public long get_idGrb() {
	return _idGrb;
}

public void set_idGrb(long _idGrb) {
	this._idGrb = _idGrb;
}

public String get_namaGrb() {
	return _namaGrb;
}

public void set_namaGrb(String _namaGrb) {
	this._namaGrb = _namaGrb;
}

}
