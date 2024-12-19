import React, { useEffect, useState } from "react";
import "./SendMail.css";
import CloseIcon from "@mui/icons-material/Close";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { login, selectUser } from "../../features/userSlice";
import { closeSendMessage } from "../../features/mailSlice";
import {
  Button,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from "@mui/material";

function SendMail() {
  const [selectedOption, setSelectedOption] = useState("Minor");
  const [send, setsend] = useState();
  const user = useSelector(selectUser);


  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();

  const dispatch = useDispatch();

  const onSubmit = async (data) => {
    const formData = new FormData();
    console.log(data.sendTheEmail);
    formData.append("senderEmail", user.email);
    formData.append("recipientEmail", data.recipientEmail);
    formData.append("subject", data.subject);
    formData.append("textBody", data.textBody);
    formData.append("sendTheEmail", data.sendTheEmail);
    formData.append("priority", selectedOption);
    if (data.attachments) {
      console.log(data.attachments);
      Array.from(data.attachments).forEach((file) =>
        formData.append("attachments", file)
      );
    }

    try {
      const response = await fetch("http://localhost:8080/api/user/createEmail", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(errorDetails.message || "Failed to send email.");
      }
      alert("Email sent successfully!");
      dispatch(closeSendMessage());
    } catch (error) {
      console.error("Error sending email:", error);
      alert("Error sending email.");
    }
  };
  const handelClose = async () => {
    setValue("sendTheEmail", false);
    dispatch(closeSendMessage());
    handleSubmit(onSubmit)();
    };

  return (
    <div className="sendMail">
      <div className="sendMail-header">
        <h3>New Message</h3>
        <FormControl className="sendMail-dropdown" size="small">
          <InputLabel id="option-select-label">Priority</InputLabel>
          <Select
            labelId="option-select-label"
            value={selectedOption}
            onChange={handleOptionChange}
          >
            <MenuItem value="Minor">Minor</MenuItem>
            <MenuItem value="Moderate">Moderate</MenuItem>
            <MenuItem value="Urgent">Urgent</MenuItem>
            <MenuItem value="Important">Important</MenuItem>
          </Select>
        </FormControl>
        <CloseIcon
          onClick={() => handelClose()}
          className="sendMail-close"
        />
      </div>

      <form onSubmit={handleSubmit(onSubmit)}>
        <input
          name="recipientEmail"
          placeholder="To"
          type="text"
          {...register("recipientEmail", { required: "Recipient email is required!" })}
        />
        {errors.recipientEmail && (
          <p className="sendMail-error">{errors.recipientEmail.message}</p>
        )}
        <input
          name="subject"
          placeholder="Subject"
          type="text"
          {...register("subject", { required: "Subject is required!" })}
        />
        {errors.subject && (
          <p className="sendMail-error">{errors.subject.message}</p>
        )}
        <input
          name="textBody"
          placeholder="Message"
          type="text"
          className="sendMail-message"
          {...register("textBody", { required: "Message is required!" })}
        />
        {errors.textBody && (
          <p className="sendMail-error">{errors.textBody.message}</p>
        )}
        <input
        type="hidden"
        value="true"
        {...register("sendTheEmail")}
        />
        <input
          type="file"
          multiple
          onChange={(e) => {
            const files = e.target.files;
            e.target.setCustomValidity("");
            register("attachments").onChange({
              target: { name: "attachments", value: files },
            });
          }}
          className="sendMail-fileInput"
          />
        <div className="sendMail-options">
          <Button
            type="submit"
            variant="contained"
            color="primary"
            className="sendMail-send"
          >
            Send
          </Button>
        </div>
      </form>
    </div>
  );
}

export default SendMail;