import React from "react";
import "./SendMail.css";
import CloseIcon from "@mui/icons-material/Close";
// import { Button } from "@mui/material";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { closeSendMessage } from "../../features/mailSlice";
import { Button, MenuItem, Select, FormControl, InputLabel } from "@mui/material";


function SendMail() {
  const [selectedOption, setSelectedOption] = React.useState("Minor");
  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const dispatch = useDispatch();



  return (
    <div className="sendMail">
      <div className="sendMail-header">
        <h3>New Message</h3>
        <FormControl className="sendMail-dropdown" variant="outlined" size="small">
          <InputLabel id="option-select-label"></InputLabel>
          <Select
            labelId="option-select-label"
            value={selectedOption}
            onChange={handleOptionChange}
            label="Select Option"
          >
            <MenuItem value="Minor">Minor</MenuItem>
            <MenuItem value="Moderate">Moderate</MenuItem>
            <MenuItem value="Urgent">Urgent</MenuItem>
            <MenuItem value="Important">Important</MenuItem>
          </Select>
        </FormControl>
        <CloseIcon
          onClick={() => dispatch(closeSendMessage())}
          className="sendMail-close"
        />
      </div>

      <form >
        <input
          name="to"
          placeholder="To"
          type="email"
          {...register("to", { required: true })}
        />
        {errors.to && <p className="sendMail-error">To is Required!</p>}
        <input
          name="subject"
          placeholder="Subject"
          type="text"
          {...register("subject", { required: true })}
        />
        {errors.subject && (
          <p className="sendMail-error">Subject is Required!</p>
        )}
        <input
          name="message"
          placeholder="Message"
          type="text"
          className="sendMail-message"
          {...register("message", { required: true })}
        />
        {errors.message && (
          <p className="sendMail-error">Message is Required!</p>
        )}
        <div className="sendMail-options">
          <Button
            variant="contained"
            color="primary"
            className="sendMail-send"
            onClick={() => dispatch(closeSendMessage())}
          >
            Send
          </Button>
        </div>
      </form>
    </div>
  );
}

export default SendMail;
