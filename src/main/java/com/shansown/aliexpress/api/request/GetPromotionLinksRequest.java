package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.error.ApiError;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.GetPromotionLinksResult;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;

import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.TRACKING_ID_KEY;
import static com.shansown.aliexpress.api.AliApi.URLS_KEY;
import static com.shansown.aliexpress.api.ApiMethod.GET_PROMOTION_LINKS;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetPromotionLinksRequest extends BaseAliRequest<GetPromotionLinksResult> {

  private static final ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>>
      RESULT_TYPE = new ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>>() {
  };

  @Builder
  private GetPromotionLinksRequest(AliAccessProperty access, String fields, String urls) {
    super(access, GET_PROMOTION_LINKS);
    Objects.requireNonNull(fields, "Fields property required");
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(TRACKING_ID_KEY, access.getTrackingId());
    putParamIfPresent(URLS_KEY, urls);
  }

  @Override
  public ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>> getResultType() {
    return RESULT_TYPE;
  }

  @Override
  public Optional<? extends ApiError> getErrorByCode(Long code) {
    return Optional.empty();
  }

  @Getter
  @RequiredArgsConstructor
  private enum Error implements ApiError {
    SYSTEM(20020000, "System Error"),
    UNAUTHORIZED_REQUEST(20030000, "Unauthorized transfer request"),
    REQUIRED_PARAMETERS(20030010, "Required parameters"),
    INVALID_PROTOCOL(20030020, "Invalid protocol format"),
    API_VERSION(20030030, "API version input parameter error"),
    NAME_SPACE(20030040, "API name space input parameter error"),
    NAME(20030050, "API name space input parameter error"),
    FIELDS(20030060, "Fields input parameter error"),
    TRACKING_ID(20030070, "Tracking ID input parameter error"),
    URL(20030080, "URL input parameter error or beyond the maximum number of the URLs");

    private final long code;
    private final String msg;
  }
}
