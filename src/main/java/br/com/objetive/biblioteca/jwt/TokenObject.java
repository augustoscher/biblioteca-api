package br.com.objetive.biblioteca.jwt;

public class TokenObject {
	
	private  String access_token;
    private String tenant;
	
	public TokenObject(String tokrn) {
		this.access_token = tokrn;
	}

    public TokenObject(String tokrn, String tnant) {
        this.access_token = tokrn;
        this.tenant = tnant;
    }

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
	
}
