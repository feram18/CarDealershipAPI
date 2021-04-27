package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Gender.class, AddressMapper.class})
public interface ClientMapper {
    @Mapping(source = "salesAssociate.id", target = "salesAssociateId")
    ClientDto toDto(Client client);

    @InheritInverseConfiguration
    @Mapping(target = "salesAssociate", ignore = true)
    Client fromDto(ClientDto clientDto);
}
