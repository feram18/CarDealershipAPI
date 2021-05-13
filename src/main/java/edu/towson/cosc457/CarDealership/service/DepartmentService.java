package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MechanicService mechanicService;

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getDepartments() {
        return StreamSupport
                .stream(departmentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.DEPARTMENT.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Department deleteDepartment(Long id) {
        Department department = getDepartment(id);
        departmentRepository.delete(department);
        return department;
    }

    @Transactional
    public Department editDepartment(Long id, Department department) {
        Department departmentToEdit = getDepartment(id);
        departmentToEdit.setId(department.getId());
        departmentToEdit.setName(department.getName());
        departmentToEdit.setManager(department.getManager());
        departmentToEdit.setLocation(department.getLocation());
        departmentToEdit.setMechanics(department.getMechanics());
        departmentToEdit.setSalesAssociates(department.getSalesAssociates());
        return departmentToEdit;
    }

    @Transactional
    public Department assignMechanic(Long departmentId, Long mechanicId) {
        Department department = getDepartment(departmentId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        if (Objects.nonNull(mechanic.getDepartment())) {
            throw new AlreadyAssignedException(
                    Entity.MECHANIC.toString(),
                    mechanicId,
                    Entity.DEPARTMENT.toString(),
                    mechanic.getDepartment().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        department.assignMechanic(mechanic);
        mechanic.setDepartment(department);
        return department;
    }

    @Transactional
    public Department removeMechanic(Long departmentId, Long mechanicId) {
        Department department = getDepartment(departmentId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        department.removeMechanic(mechanic);
        return department;
    }
}
