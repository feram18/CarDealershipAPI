package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentMapperTest {
    private static DepartmentMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new DepartmentMapperImpl();
    }

    @Test
    void shouldMapToDto() {
        Department department = Department.builder()
                .id(1L)
                .name("Department A")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .location(Location.builder()
                        .id(1L)
                        .build())
                .build();

        DepartmentDto departmentDto = mapper.toDto(department);

        assertAll(() -> {
            assertThat(departmentDto).isInstanceOf(DepartmentDto.class);
            assertEquals(departmentDto.getId(), department.getId());
            assertEquals(departmentDto.getName(), department.getName());
            assertEquals(departmentDto.getManagerId(), department.getManager().getId());
            assertEquals(departmentDto.getLocationId(), department.getLocation().getId());
        });
    }

    @Test
    void shouldMapFromDto() {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(1L)
                .name("Department A")
                .build();

        Department department = mapper.fromDto(departmentDto);

        assertAll(() -> {
            assertThat(department).isInstanceOf(Department.class);
            assertEquals(department.getId(), departmentDto.getId());
            assertEquals(department.getName(), departmentDto.getName());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Department department = mapper.fromDto(null);

        assertThat(department).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        DepartmentDto departmentDto = mapper.toDto(null);

        assertThat(departmentDto).isEqualTo(null);
    }
}
