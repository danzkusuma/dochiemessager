package core.messager.dochie.bean;

public class user_dochie {
	private long _idUsr;
	private String _namaUsr;
	private String _emailUsr;
	private String _nohpUsr;
	private String _sinUsr;
	private int _isdochie;

	public user_dochie() {
		super();
	}

	public user_dochie(String _namaUsr, String _emailUsr, String _nohpUsr,
			String _sinUsr, int _isdochie) {
		super();
		this._namaUsr = _namaUsr;
		this._emailUsr = _emailUsr;
		this._nohpUsr = _nohpUsr;
		this._sinUsr = _sinUsr;
		this._isdochie = _isdochie;
	}

	public user_dochie(long _idUsr, String _namaUsr, String _emailUsr,
			String _nohpUsr, String _sinUsr, int _isdochie) {
		super();
		this._idUsr = _idUsr;
		this._namaUsr = _namaUsr;
		this._emailUsr = _emailUsr;
		this._nohpUsr = _nohpUsr;
		this._sinUsr = _sinUsr;
		this._isdochie = _isdochie;
	}

	public long get_idUsr() {
		return _idUsr;
	}

	public void set_idUsr(long _idUsr) {
		this._idUsr = _idUsr;
	}

	public String get_namaUsr() {
		return _namaUsr;
	}

	public void set_namaUsr(String _namaUsr) {
		this._namaUsr = _namaUsr;
	}

	public String get_emailUsr() {
		return _emailUsr;
	}

	public void set_emailUsr(String _emailUsr) {
		this._emailUsr = _emailUsr;
	}

	public String get_nohpUsr() {
		return _nohpUsr;
	}

	public void set_nohpUsr(String _nohpUsr) {
		this._nohpUsr = _nohpUsr;
	}

	public String get_sinUsr() {
		return _sinUsr;
	}

	public void set_sinUsr(String _sinUsr) {
		this._sinUsr = _sinUsr;
	}

	public int get_isdochie() {
		return _isdochie;
	}

	public void set_isdochie(int _isdochie) {
		this._isdochie = _isdochie;
	}

}
