package com.brunotacca.domain.usecases.customer.create;
import com.brunotacca.domain.usecases.shared.AbstractUseCase;
class CreateCustomerUseCase extends AbstractUseCase.WithParamWithReturn<CreateCustomerInputDTO, Object> {

  @Override
  public Object execute(CreateCustomerInputDTO param) {
    // Validate Input TODO
      // if wrong input output readable error TODO

    // Validate Unique Email TODO
      // if not unique output readable error TODO

    // Create TODO
      // save TODO

    // Return with ID TODO
    return null;
  }
  
}
