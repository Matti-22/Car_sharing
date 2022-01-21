package com.company.db.company;

import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies();
    public void addCompany(Company company);
    public void deleteCompany(Company company);
}
