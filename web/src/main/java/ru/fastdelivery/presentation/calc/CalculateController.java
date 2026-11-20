package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.weight.Weight;
import ru.fastdelivery.domain.common.dimension.width.Width;
import ru.fastdelivery.domain.coordinate.Departure;
import ru.fastdelivery.domain.coordinate.Destination;
import ru.fastdelivery.domain.coordinate.Route;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        var packsWeights = request.packages().stream()
                .map(cargo -> new Pack(
                        new Weight(cargo.weight()),
                        new Length(cargo.length()),
                        new Width(cargo.width()),
                        new Height(cargo.height())
                ))
                .toList();

        var destination = new Destination(request.destination().latitude(), request.destination().longitude());
        var departure = new Departure(request.departure().latitude(), request.departure().longitude());
        var ruote = new Route(destination, departure);

        var shipment = new Shipment(packsWeights, currencyFactory.create(request.currencyCode()));
        var calculatedPrice = tariffCalculateUseCase.getTotalCost(shipment, ruote);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();

        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }
}

