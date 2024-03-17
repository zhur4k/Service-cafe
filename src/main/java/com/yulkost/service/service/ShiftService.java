package com.yulkost.service.service;

import com.yulkost.service.dto.ShiftReportDto;
import com.yulkost.service.dto.mapper.ShiftReportDtoMapper;
import com.yulkost.service.model.Collection;
import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import com.yulkost.service.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShiftService {
    private final UserService userService;
    private final ShiftRepository shiftRepository;
    private final ShiftReportDtoMapper shiftReportDtoMapper;
    private final CashRegisterRestService cashRegisterRestService;
    private final CashRegisterService cashRegisterService;

    public ShiftService(UserService userService, ShiftRepository shiftRepository, ShiftReportDtoMapper shiftReportDtoMapper, CashRegisterRestService cashRegisterRestService, CashRegisterService cashRegisterService) {
        this.userService = userService;
        this.shiftRepository = shiftRepository;
        this.shiftReportDtoMapper = shiftReportDtoMapper;
        this.cashRegisterRestService = cashRegisterRestService;
        this.cashRegisterService = cashRegisterService;
    }
    public List<ShiftReportDto> findAllSameByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return shiftRepository.findByStartDateBetween(startDate,endDate)
                .stream()
                .map(shiftReportDtoMapper)
                .collect(Collectors.toList());
    }
    public Shift openShift(User user) {
        Shift openShift = getOpenShift();
        if(openShift==null)  {
        Shift shift = new Shift();
            shift.getUsers().add(userService.save(user));
            Collection collection = new Collection();
            collection.setSumOfOperation(cashRegisterService.getSumInCashRegisterInteger());
            collection.setShift(shift);
            collection.setTypeOfOperation(true);
            cashRegisterRestService.sendIOCheck(collection);
            return shiftRepository.save(shift);
        }
        return openShift;
    }
    public Shift addUser(Shift shift, String login) {
        User user = userService.findByLogin(login);
        System.out.println(login);
        if (user == null) {
            throw new RuntimeException("Пользователь с таким логином не найден!");
        }


        if (shift.getUsers() != null && shift.getUsers().contains(user)) {
            throw new RuntimeException("Пользователь уже добавлен!!!");
        }

        List<User> users = new ArrayList<>();
        if (shift.getUsers() != null) {
            users.addAll(shift.getUsers());
        }
        users.add(user); // Добавьте объект User в список
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
