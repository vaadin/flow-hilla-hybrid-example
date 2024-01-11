import React, {
  type ReactNode,
} from 'react';
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";
import Rating from "@mui/material/Rating";

class MuiRatingElement extends ReactAdapterElement {
  protected override render(): ReactNode {
    const [rating, setRating] = this.useState<number>("rating");
    return <Rating value={rating} onChange={(_, value) => setRating(value == null ? 0 : value)}/>;
  }
}

customElements.define("mui-rating", MuiRatingElement);
