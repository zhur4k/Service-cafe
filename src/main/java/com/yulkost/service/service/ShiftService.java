package com.yulkost.service.service;

import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import com.yulkost.service.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class ShiftService {
    private UserService userService;
    private ShiftRepository shiftRepository;

    public ShiftService(UserService userService, ShiftRepository shiftRepository) {
        this.userService = userService;
        this.shiftRepository = shiftRepository;
    }

    public Shift openShift(User user) {
        Shift openShift = getOpenShift();
        if(openShift==null)  {
        Shift shift = new Shift();
            shift.getUsers().add(userService.save(user));
            return shiftRepository.save(shift);
        }
        return openShift;
    }
    public Shift addUser(Shift shift,String login){
            User user = userService.findByLogin(login);
        if (shift.getUsers().contains(user))
                throw new RuntimeException("Пользователь уже добавлен!!!");
        List<User> users = shift.getUsers();
        users.add(user);
        shift.setUsers(users);
        return shiftRepository.save(shift);
    }
    public void shiftIsOpen(){
        if(getOpenShift()==null){
            throw new RuntimeException("Откройте смену");
        }
    }
    public Shift getOpenShift() {
        Shift openShift = shiftRepository.findByStateOfShift(true);
        if(openShift!=null&&openShift.getStartDate().until(LocalDateTime.now(), ChronoUnit.HOURS)>=24){
            openShift.setStateOfShift(false);
            openShift.setEndDate(openShift.getStartDate().plusHours(24));
            shiftRepository.save(openShift);
            return null;
        }
        return openShift;
    }
    public void deleteShift(Shift shift){
        shiftRepository.delete(shift);
    }
    public void closeShift(Shift shift){
        shift.setStateOfShift(false);
        shift.setEndDate(LocalDateTime.now());
        shiftRepository.save(shift);
    }

    public Shift save(Shift openShift) {
        return shiftRepository.save(openShift);
    }

    public String getListOfUsers() {
        StringBuilder listOfUsers = new StringBuilder("Работники на смене:<br>");
        for(User user:getOpenShift().getUsers()) {
            if(Objects.equals(user.getRoles().toString(), "[ADMIN]")){
                listOfUsers.append("Админ");
            }
            if(Objects.equals(user.getRoles().toString(), "[CASHIER]")){
                listOfUsers.append("Продавец");
            }
            listOfUsers.append("   ").append(user.getName()).append("<br>");
        }
        return listOfUsers.toString();
    }
}
