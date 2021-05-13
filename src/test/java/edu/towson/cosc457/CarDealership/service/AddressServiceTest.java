package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Captor
    private ArgumentCaptor<Address> addressArgumentCaptor;
    private Address address;
    private Address editedAddress;

    @BeforeEach
    public void setUp() {
        address = Address.builder()
                .id(1L)
                .street("123 Main St.")
                .city("New York City")
                .state("New York")
                .zipCode(12345)
                .build();

        editedAddress = Address.builder()
                .id(1L)
                .street("456 Main St.")
                .city("San Francisco")
                .state("California")
                .zipCode(78951)
                .build();
    }

    @Test
    @DisplayName("Should save Address")
    void shouldSaveAddress() {
        addressService.addAddress(address);

        verify(addressRepository, times(1)).save(addressArgumentCaptor.capture());

        assertAll(() -> {
            assertThat(addressArgumentCaptor.getValue()).isNotNull();
            assertThat(addressArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(address);
        });
    }

    @Test
    void shouldFailToSaveNullAddress() {
        addressService.addAddress(null);

        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    @DisplayName("Should find Address by Id")
    void shouldGetAddressById() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        Address actualAddress = addressService.getAddress(address.getId());
        verify(addressRepository, times(1)).findById(address.getId());

        assertAll(() -> {
            assertThat(actualAddress).isNotNull();
            assertThat(actualAddress).usingRecursiveComparison().isEqualTo(address);
        });
    }

    @Test
    void shouldFailToGetAddressById() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> addressService.getAddress(address.getId()));

        verify(addressRepository, times(1)).findById(address.getId());
    }

    @Test
    @DisplayName("Should find all Addresses")
    void shouldGetAllAddresses() {
        List<Address> expectedAddresses = new ArrayList<>();
        expectedAddresses.add(address);
        expectedAddresses.add(Address.builder()
                .id(2L)
                .build());
        expectedAddresses.add(Address.builder()
                .id(3L)
                .build());

        Mockito.when(addressRepository.findAll()).thenReturn(expectedAddresses);
        List<Address> actualAddresses = addressService.getAddresses();
        verify(addressRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualAddresses).isNotNull();
            assertThat(actualAddresses.size()).isEqualTo(expectedAddresses.size());
        });
    }

    @Test
    void shouldGetAllAddresses_EmptyList() {
        Mockito.when(addressRepository.findAll()).thenReturn(new ArrayList<>());

        List<Address> actualAddresses = addressService.getAddresses();

        assertThat(actualAddresses).isEmpty();
    }

    @Test
    @DisplayName("Should delete Address by Id")
    void shouldDeleteAddress() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        Address deletedAddress = addressService.deleteAddress(address.getId());

        verify(addressRepository, times(1)).delete(address);

        assertAll(() -> {
            assertThat(deletedAddress).isNotNull();
            assertThat(deletedAddress).usingRecursiveComparison().isEqualTo(address);
        });
    }

    @Test
    void shouldFailToDeleteAddress() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> addressService.deleteAddress(address.getId()));

        verify(addressRepository, never()).delete(any(Address.class));
    }

    @Test
    @DisplayName("Should update Address entity")
    void shouldUpdateAddress() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        Address updatedAddress = addressService.editAddress(address.getId(), editedAddress);

        assertAll(() -> {
            assertThat(updatedAddress).isNotNull();
            assertThat(updatedAddress).usingRecursiveComparison().isEqualTo(editedAddress);
        });
    }

    @Test
    void shouldFailToUpdateAddress() {
        Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> addressService.editAddress(address.getId(), editedAddress));
    }
}