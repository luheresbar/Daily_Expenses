package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.domain.dto.UserRolDto;
import com.luheresbar.daily.domain.repository.IUserRepository;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserRepository userRepository;


    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<IUserSummary> viewUsersSummary() {
        return userRepository.viewUsersSummary();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public boolean exists(Integer userId) {
        return this.userRepository.existsById(userId);
    }

    public Optional<User> getById(Integer userId) {
        return userRepository.getById(userId);
    }

    public boolean delete(Integer userId) {
        return  this.userRepository.delete(userId);
    }

    public Integer countUsers() {
        return this.userRepository.countUsers();
    }

    public void updateUserId(UpdateUserIdDto updateUserIdDto) {
        this.userRepository.updateUserId(updateUserIdDto);

    }
    public boolean changePassword(String email, String newPassword) {
        return this.userRepository.changePassword(email, newPassword);
    }

//    public List<CategoryDto> getCategoryDto(Optional<User> userDB) {
//        List<ExpenseCategory> categories = userDB.get().getCategories();
//        List<CategoryDto> categoryNames = categories.stream()
//                .map(category -> new CategoryDto(category.getCategoryName()))
//                .collect(Collectors.toList());
//        return categoryNames;
//    }
//
//    public List<SummaryAccountsDto> getAccountDto(Optional<User> userDB) {
//        List<Account> accounts = userDB.get().getAccounts();
//        List<SummaryAccountsDto> accountNames = accounts.stream()
//                .filter(account -> account.getAvailable()) // Filtrar solo las cuentas con available = true
//                .map(account -> new SummaryAccountsDto(account.getAccountName()))
//                .collect(Collectors.toList());
//        return accountNames;
//    }

}
