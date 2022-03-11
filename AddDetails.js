import React ,{useState,useEffect} from 'react'
import axios from 'axios'
function AddCompany() {
      const [compInfo, setCompInfo] = useState({});

const handleSubmit = (event) => {

    event.preventDefault();

    axios.post('http://localhost:4040/api/v1/companies', compInfo)
    .then(response => console.log('record posted'));

  };

   const handleChange = (event) => {
   const name = event.target.name;
   const value = event.target.value;
   setCompInfo(values => ({...values, [name]: value}))
   }
    
  return (
<div className="form-container">
      <form onSubmit={handleSubmit}> 
          <input
            type="text"
            name="companyId"
            placeholder="Id of the comapnay"
            value={compInfo.companyId || ""}
            onChange={ handleChange}/>
            
                    <button>Submit</button>

          </form>
        </div>
    
    )
}

export default AddCompany
