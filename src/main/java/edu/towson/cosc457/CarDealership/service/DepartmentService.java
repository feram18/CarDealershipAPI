package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    /**
     * Create a new Department in the database
     * @param department Department object to be added to database
     * @return Department saved on repository
     */
    public Department addDepartment(Department department) {
        LOGGER.info("Create new Department in the database");
        return departmentRepository.save(department);
    }

    /**
     * Get All Departments
     * @return List of Departments
     */
    public List<Department> getDepartments() {
        LOGGER.info("Get all Departments");
        return StreamSupport
                .stream(departmentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Department by Id
     * @param id identifier of Department to be fetched
     * @return fetched Department
     * @throws NotFoundException if no Department with matching id found
     */
    public Department getDepartment(Long id) {
        LOGGER.info("Get Department with id {}", id);
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.DEPARTMENT.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Department by Id
     * @param id identifier of Department to be deleted
     * @return deleted Department
     * @throws NotFoundException if no Department with matching id found
     */
    public Department deleteDepartment(Long id) {
        LOGGER.info("Delete Department with id {}", id);
        Department department = getDepartment(id);
        departmentRepository.delete(department);
        return department;
    }

    /**
     * Update Department
     * @param id identifier of Department to be updated
     * @param department Department object with updated fields
     * @return updated Department
     * @throws NotFoundException if no Department with matching id found
     */
    @Transactional
    public Department editDepartment(Long id, Department department) {
        LOGGER.info("Update Department with id {}", id);
        Department departmentToEdit = getDepartment(id);
        departmentToEdit.setName(department.getName());
        departmentToEdit.setManager(department.getManager());
        departmentToEdit.setLocation(department.getLocation());
        departmentToEdit.setMechanics(department.getMechanics());
        departmentToEdit.setSalesAssociates(department.getSalesAssociates());
        return departmentToEdit;
    }

    /**
     * Assign Mechanic to Department
     * @param departmentId identifier of Department to be updated
     * @param mechanicId identifier of Mechanic to be assigned to Department
     * @return updated Department entity
     * @throws NotFoundException if no Department or Mechanic with matching departmentId/mechanicId were found
     * @throws AlreadyAssignedException if Mechanic has already been assigned to a Department
     */
    @Transactional
    public Department assignMechanic(Long departmentId, Long mechanicId) {
        LOGGER.info("Assign Mechanic with id {} to Department with id {}", mechanicId, departmentId);
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

    /**
     * Remove Mechanic from Department
     * @param departmentId identifier of Department to be updated
     * @param mechanicId identifier of Mechanic to be removed from Department
     * @return updated Department entity
     * @throws NotFoundException if no Department or Mechanic with matching departmentId/mechanicId were found
     */
    @Transactional
    public Department removeMechanic(Long departmentId, Long mechanicId) {
        LOGGER.info("Remove Mechanic with id {} from Department with id {}", mechanicId, departmentId);
        Department department = getDepartment(departmentId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        department.removeMechanic(mechanic);
        return department;
    }
}
