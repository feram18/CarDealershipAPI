package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Gender.class, AddressMapper.class})
public interface ClientMapper {
    /**
     * Map from Client entity to ClientDTO
     * @param client Client object to be mapped to DTO
     * @return mapped ClientDto object
     */
    @Mapping(source = "salesAssociate.id", target = "salesAssociateId")
    ClientDto toDto(Client client);

    /**
     * Map from ClientDTO to Client entity
     * @param clientDto ClientDTO object to be mapped to Client entity
     * @return mapped Client object
     */
    @InheritInverseConfiguration
    @Mapping(target = "salesAssociate", ignore = true)
    Client fromDto(ClientDto clientDto);
}
