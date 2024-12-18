import React from "react";
import "./Mail.css";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import MoveToInboxIcon from "@mui/icons-material/MoveToInbox";
import ErrorIcon from "@mui/icons-material/Error";
import DeleteIcon from "@mui/icons-material/Delete";
import EmailIcon from "@mui/icons-material/Email";
import WatchLaterIcon from "@mui/icons-material/WatchLater";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import LabelImportantIcon from "@mui/icons-material/LabelImportant";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import UnfoldMoreIcon from "@mui/icons-material/UnfoldMore";
import PrintIcon from "@mui/icons-material/Print";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import { IconButton } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import { selectOpenMail } from "../../features/mailSlice";
import { useSelector } from "react-redux";

function Mail() {
    const navigate = useNavigate();

  const selectedMail = useSelector(selectOpenMail);
  const generateDataUrl = (attachment) => {
    return `data:${attachment.attType};base64,${attachment.attachment}`;
  };

  return (
    <div className="mail">
      <div className="mail-tools">
        <div className="mail-toolsLeft">
          <IconButton onClick={() => navigate("/emaillist")}>
            <ArrowBackIcon />
          </IconButton>

          <IconButton>
            <MoveToInboxIcon />
          </IconButton>

          <IconButton>
            <DeleteIcon />
          </IconButton>

          <IconButton>
            <LabelImportantIcon />
          </IconButton>

          <IconButton>
            <MoreVertIcon />
          </IconButton>
        </div>
      </div>
      <div className="mail-body">
        <div className="mail-bodyHeader">
          <div className="mail-subject">
            <h2>{selectedMail?.subject}</h2>
          </div>
          <p>{selectedMail?.sender}</p>
          <p className="mail-time">{selectedMail?.timeStamp}</p>
        </div>

        <div className="mail-message">
          <p>{selectedMail?.textBody}</p>
        </div>
        {selectedMail?.attachments && selectedMail.attachments.length > 0 && (
          <div className="mail-attachments">
            <h4>Attachments:</h4>
            <div className="mail-attachmentGrid">
              {selectedMail.attachments.map((attachment, index) => {
                if (attachment.attType.startsWith("image/")) {
                  // Render images inline
                  return (
                    <div key={index} className="mail-attachmentImage">
                      <img
                        src={generateDataUrl(attachment)}
                        alt={attachment.attName}
                        title={`${attachment.attName} (${attachment.attSize.toFixed(2)} KB)`}
                      />
                      <div key={index} className="mail-attachmentLink">
                      <a
                        href={generateDataUrl(attachment)}
                        download={attachment.attName}
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        {attachment.attName} ({attachment.attSize.toFixed(2)} KB)
                      </a>
                    </div>
                    </div>
                  );
                } else {
                  // Render non-image files as download links
                  return (
                    <div key={index} className="mail-attachmentLink">
                      <a
                        href={generateDataUrl(attachment)}
                        download={attachment.attName}
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        {attachment.attName} ({attachment.attSize.toFixed(2)} KB)
                      </a>
                    </div>
                  );
                }
              })}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Mail;
