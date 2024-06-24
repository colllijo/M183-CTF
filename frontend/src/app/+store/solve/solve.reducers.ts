import { createFeature, createReducer, on } from '@ngrx/store';
import {SolveActions} from "@+store/solve/solve.actions";
import {SolveState} from "@+store/solve/solve.models";

const initialState: SolveState = {
  solve: null,
  loading: false,
  errors: null
};

export const solveFeature = createFeature({
  name: 'solve',
  reducer: createReducer(
    initialState,
    on(
      SolveActions.getSolve,
      (state): SolveState => ({
        ...state,
        solve: null,
        errors: null,
        loading: true
      })
    ),
    on(
      SolveActions.getSolveSuccess,
      (state, { solve }): SolveState => ({
        ...state,
        solve,
        errors: null,
        loading: false
      })
    ),
    on(
      SolveActions.getSolveFailure,
      (state, { errors }): SolveState => ({
        ...state,
        errors,
        loading: false
      })
    ),
  )
});
