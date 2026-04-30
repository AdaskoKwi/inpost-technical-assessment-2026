package pl.kul.inpost_assessment.DTO;

public record AddressDTO(
        String city,
        String province,
        String post_code,
        String street,
        String building_number
) {
}
